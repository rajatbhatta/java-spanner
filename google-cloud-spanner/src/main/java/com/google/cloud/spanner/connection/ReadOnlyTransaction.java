/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.spanner.connection;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.Timestamp;
import com.google.cloud.spanner.CommitResponse;
import com.google.cloud.spanner.DatabaseClient;
import com.google.cloud.spanner.ErrorCode;
import com.google.cloud.spanner.Mutation;
import com.google.cloud.spanner.Options.UpdateOption;
import com.google.cloud.spanner.ReadContext;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.SpannerException;
import com.google.cloud.spanner.SpannerExceptionFactory;
import com.google.cloud.spanner.TimestampBound;
import com.google.cloud.spanner.connection.AbstractStatementParser.ParsedStatement;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

/**
 * Transaction that is used when a {@link Connection} is in read-only mode or when the transaction
 * mode is set to read-only. This transaction can only be used to execute queries.
 */
class ReadOnlyTransaction extends AbstractMultiUseTransaction {
  private final DatabaseClient dbClient;
  private final TimestampBound readOnlyStaleness;
  private com.google.cloud.spanner.ReadOnlyTransaction transaction;
  private UnitOfWorkState state = UnitOfWorkState.STARTED;

  static class Builder extends AbstractBaseUnitOfWork.Builder<Builder, ReadOnlyTransaction> {
    private DatabaseClient dbClient;
    private TimestampBound readOnlyStaleness;

    private Builder() {}

    Builder setDatabaseClient(DatabaseClient client) {
      Preconditions.checkNotNull(client);
      this.dbClient = client;
      return this;
    }

    Builder setReadOnlyStaleness(TimestampBound staleness) {
      Preconditions.checkNotNull(staleness);
      this.readOnlyStaleness = staleness;
      return this;
    }

    @Override
    ReadOnlyTransaction build() {
      Preconditions.checkState(dbClient != null, "No DatabaseClient client specified");
      Preconditions.checkState(readOnlyStaleness != null, "No ReadOnlyStaleness specified");
      return new ReadOnlyTransaction(this);
    }
  }

  static Builder newBuilder() {
    return new Builder();
  }

  @VisibleForTesting
  ReadOnlyTransaction(Builder builder) {
    super(builder);
    this.dbClient = builder.dbClient;
    this.readOnlyStaleness = builder.readOnlyStaleness;
  }

  @Override
  public UnitOfWorkState getState() {
    return this.state;
  }

  @Override
  public boolean isReadOnly() {
    return true;
  }

  @Override
  void checkAborted() {
    // No-op for read-only transactions as they cannot abort.
  }

  @Override
  void checkOrCreateValidTransaction(ParsedStatement statement, CallType callType) {
    if (transaction == null) {
      transaction = dbClient.readOnlyTransaction(readOnlyStaleness);
    }
  }

  @Override
  ReadContext getReadContext() {
    ConnectionPreconditions.checkState(transaction != null, "Missing read-only transaction");
    return transaction;
  }

  @Override
  public Timestamp getReadTimestamp() {
    ConnectionPreconditions.checkState(
        transaction != null, "There is no read timestamp available for this transaction.");
    ConnectionPreconditions.checkState(
        state != UnitOfWorkState.ROLLED_BACK, "This transaction was rolled back");
    return transaction.getReadTimestamp();
  }

  @Override
  public Timestamp getReadTimestampOrNull() {
    if (transaction != null && state != UnitOfWorkState.ROLLED_BACK) {
      try {
        return transaction.getReadTimestamp();
      } catch (SpannerException e) {
        // ignore
      }
    }
    return null;
  }

  @Override
  public Timestamp getCommitTimestamp() {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION,
        "There is no commit timestamp available for this transaction.");
  }

  @Override
  public Timestamp getCommitTimestampOrNull() {
    return null;
  }

  @Override
  public CommitResponse getCommitResponse() {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION,
        "There is no commit response available for read-only transactions.");
  }

  @Override
  public CommitResponse getCommitResponseOrNull() {
    return null;
  }

  @Override
  public ApiFuture<Void> executeDdlAsync(CallType callType, ParsedStatement ddl) {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION, "DDL statements are not allowed for read-only transactions");
  }

  @Override
  public ApiFuture<Long> executeUpdateAsync(
      CallType callType, ParsedStatement update, UpdateOption... options) {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION,
        "Update statements are not allowed for read-only transactions");
  }

  @Override
  public ApiFuture<ResultSet> analyzeUpdateAsync(
      CallType callType, ParsedStatement update, AnalyzeMode analyzeMode, UpdateOption... options) {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION,
        "Analyzing updates is not allowed for read-only transactions");
  }

  @Override
  public ApiFuture<long[]> executeBatchUpdateAsync(
      CallType callType, Iterable<ParsedStatement> updates, UpdateOption... options) {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION, "Batch updates are not allowed for read-only transactions.");
  }

  @Override
  public ApiFuture<Void> writeAsync(CallType callType, Iterable<Mutation> mutations) {
    throw SpannerExceptionFactory.newSpannerException(
        ErrorCode.FAILED_PRECONDITION, "Mutations are not allowed for read-only transactions");
  }

  @Override
  public ApiFuture<Void> commitAsync(CallType callType) {
    if (this.transaction != null) {
      this.transaction.close();
    }
    this.state = UnitOfWorkState.COMMITTED;
    return ApiFutures.immediateFuture(null);
  }

  @Override
  public ApiFuture<Void> rollbackAsync(CallType callType) {
    if (this.transaction != null) {
      this.transaction.close();
    }
    this.state = UnitOfWorkState.ROLLED_BACK;
    return ApiFutures.immediateFuture(null);
  }

  @Override
  String getUnitOfWorkName() {
    return "read-only transaction";
  }

  Savepoint savepoint(String name) {
    // Read-only transactions do not keep track of the executed statements as they also do not take
    // any locks. There is therefore no savepoint positions that must be rolled back to.
    return Savepoint.of(name);
  }

  void rollbackToSavepoint(Savepoint savepoint) {
    // no-op for read-only transactions
  }
}
