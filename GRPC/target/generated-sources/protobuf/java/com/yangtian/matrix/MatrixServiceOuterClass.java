// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MatrixService.proto

package com.yangtian.matrix;

public final class MatrixServiceOuterClass {
  private MatrixServiceOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yangtian_matrix_Matrix_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yangtian_matrix_Matrix_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yangtian_matrix_Matrix_Row_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yangtian_matrix_Matrix_Row_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yangtian_matrix_MatrixRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yangtian_matrix_MatrixRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_yangtian_matrix_MatrixResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_yangtian_matrix_MatrixResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023MatrixService.proto\022\023com.yangtian.matr" +
      "ix\"N\n\006Matrix\022,\n\003row\030\001 \003(\0132\037.com.yangtian" +
      ".matrix.Matrix.Row\032\026\n\003Row\022\017\n\007element\030\001 \003" +
      "(\005\"_\n\rMatrixRequest\022&\n\001a\030\001 \001(\0132\033.com.yan" +
      "gtian.matrix.Matrix\022&\n\001b\030\002 \001(\0132\033.com.yan" +
      "gtian.matrix.Matrix\"8\n\016MatrixResponse\022&\n" +
      "\001c\030\001 \001(\0132\033.com.yangtian.matrix.Matrix2\302\001" +
      "\n\rMatrixService\022Z\n\rmultiplyBlock\022\".com.y" +
      "angtian.matrix.MatrixRequest\032#.com.yangt" +
      "ian.matrix.MatrixResponse\"\000\022U\n\010addBlock\022",
      "\".com.yangtian.matrix.MatrixRequest\032#.co" +
      "m.yangtian.matrix.MatrixResponse\"\000B\002P\001b\006" +
      "proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_yangtian_matrix_Matrix_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_yangtian_matrix_Matrix_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yangtian_matrix_Matrix_descriptor,
        new java.lang.String[] { "Row", });
    internal_static_com_yangtian_matrix_Matrix_Row_descriptor =
      internal_static_com_yangtian_matrix_Matrix_descriptor.getNestedTypes().get(0);
    internal_static_com_yangtian_matrix_Matrix_Row_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yangtian_matrix_Matrix_Row_descriptor,
        new java.lang.String[] { "Element", });
    internal_static_com_yangtian_matrix_MatrixRequest_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_yangtian_matrix_MatrixRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yangtian_matrix_MatrixRequest_descriptor,
        new java.lang.String[] { "A", "B", });
    internal_static_com_yangtian_matrix_MatrixResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_yangtian_matrix_MatrixResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_yangtian_matrix_MatrixResponse_descriptor,
        new java.lang.String[] { "C", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}