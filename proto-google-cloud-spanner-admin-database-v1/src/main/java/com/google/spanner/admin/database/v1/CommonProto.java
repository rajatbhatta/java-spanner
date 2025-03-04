/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/spanner/admin/database/v1/common.proto

package com.google.spanner.admin.database.v1;

public final class CommonProto {
  private CommonProto() {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

  public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
  }

  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_admin_database_v1_OperationProgress_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_admin_database_v1_OperationProgress_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_admin_database_v1_EncryptionConfig_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_admin_database_v1_EncryptionConfig_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
      internal_static_google_spanner_admin_database_v1_EncryptionInfo_descriptor;
  static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_google_spanner_admin_database_v1_EncryptionInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

  static {
    java.lang.String[] descriptorData = {
      "\n-google/spanner/admin/database/v1/commo"
          + "n.proto\022 google.spanner.admin.database.v"
          + "1\032\037google/api/field_behavior.proto\032\031goog"
          + "le/api/resource.proto\032\037google/protobuf/t"
          + "imestamp.proto\032\027google/rpc/status.proto\""
          + "\213\001\n\021OperationProgress\022\030\n\020progress_percen"
          + "t\030\001 \001(\005\022.\n\nstart_time\030\002 \001(\0132\032.google.pro"
          + "tobuf.Timestamp\022,\n\010end_time\030\003 \001(\0132\032.goog"
          + "le.protobuf.Timestamp\"P\n\020EncryptionConfi"
          + "g\022<\n\014kms_key_name\030\002 \001(\tB&\372A#\n!cloudkms.g"
          + "oogleapis.com/CryptoKey\"\302\002\n\016EncryptionIn"
          + "fo\022S\n\017encryption_type\030\003 \001(\01625.google.spa"
          + "nner.admin.database.v1.EncryptionInfo.Ty"
          + "peB\003\340A\003\0222\n\021encryption_status\030\004 \001(\0132\022.goo"
          + "gle.rpc.StatusB\003\340A\003\022I\n\017kms_key_version\030\002"
          + " \001(\tB0\340A\003\372A*\n(cloudkms.googleapis.com/Cr"
          + "yptoKeyVersion\"\\\n\004Type\022\024\n\020TYPE_UNSPECIFI"
          + "ED\020\000\022\035\n\031GOOGLE_DEFAULT_ENCRYPTION\020\001\022\037\n\033C"
          + "USTOMER_MANAGED_ENCRYPTION\020\002*\\\n\017Database"
          + "Dialect\022 \n\034DATABASE_DIALECT_UNSPECIFIED\020"
          + "\000\022\027\n\023GOOGLE_STANDARD_SQL\020\001\022\016\n\nPOSTGRESQL"
          + "\020\002B\242\004\n$com.google.spanner.admin.database"
          + ".v1B\013CommonProtoP\001ZFcloud.google.com/go/"
          + "spanner/admin/database/apiv1/databasepb;"
          + "databasepb\252\002&Google.Cloud.Spanner.Admin."
          + "Database.V1\312\002&Google\\Cloud\\Spanner\\Admin"
          + "\\Database\\V1\352\002+Google::Cloud::Spanner::A"
          + "dmin::Database::V1\352Ax\n!cloudkms.googleap"
          + "is.com/CryptoKey\022Sprojects/{project}/loc"
          + "ations/{location}/keyRings/{key_ring}/cr"
          + "yptoKeys/{crypto_key}\352A\246\001\n(cloudkms.goog"
          + "leapis.com/CryptoKeyVersion\022zprojects/{p"
          + "roject}/locations/{location}/keyRings/{k"
          + "ey_ring}/cryptoKeys/{crypto_key}/cryptoK"
          + "eyVersions/{crypto_key_version}b\006proto3"
    };
    descriptor =
        com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
            descriptorData,
            new com.google.protobuf.Descriptors.FileDescriptor[] {
              com.google.api.FieldBehaviorProto.getDescriptor(),
              com.google.api.ResourceProto.getDescriptor(),
              com.google.protobuf.TimestampProto.getDescriptor(),
              com.google.rpc.StatusProto.getDescriptor(),
            });
    internal_static_google_spanner_admin_database_v1_OperationProgress_descriptor =
        getDescriptor().getMessageTypes().get(0);
    internal_static_google_spanner_admin_database_v1_OperationProgress_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_admin_database_v1_OperationProgress_descriptor,
            new java.lang.String[] {
              "ProgressPercent", "StartTime", "EndTime",
            });
    internal_static_google_spanner_admin_database_v1_EncryptionConfig_descriptor =
        getDescriptor().getMessageTypes().get(1);
    internal_static_google_spanner_admin_database_v1_EncryptionConfig_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_admin_database_v1_EncryptionConfig_descriptor,
            new java.lang.String[] {
              "KmsKeyName",
            });
    internal_static_google_spanner_admin_database_v1_EncryptionInfo_descriptor =
        getDescriptor().getMessageTypes().get(2);
    internal_static_google_spanner_admin_database_v1_EncryptionInfo_fieldAccessorTable =
        new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
            internal_static_google_spanner_admin_database_v1_EncryptionInfo_descriptor,
            new java.lang.String[] {
              "EncryptionType", "EncryptionStatus", "KmsKeyVersion",
            });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.google.api.FieldBehaviorProto.fieldBehavior);
    registry.add(com.google.api.ResourceProto.resourceDefinition);
    registry.add(com.google.api.ResourceProto.resourceReference);
    com.google.protobuf.Descriptors.FileDescriptor.internalUpdateFileDescriptor(
        descriptor, registry);
    com.google.api.FieldBehaviorProto.getDescriptor();
    com.google.api.ResourceProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
    com.google.rpc.StatusProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
