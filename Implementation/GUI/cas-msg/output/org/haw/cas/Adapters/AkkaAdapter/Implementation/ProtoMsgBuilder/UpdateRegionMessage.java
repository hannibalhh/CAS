// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: messages/region/UpdateRegionMessage.proto

package org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder;

public final class UpdateRegionMessage {
  private UpdateRegionMessage() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface UpdateRegionOrBuilder
      extends com.google.protobuf.MessageOrBuilder {
  }
  public static final class UpdateRegion extends
      com.google.protobuf.GeneratedMessage
      implements UpdateRegionOrBuilder {
    // Use UpdateRegion.newBuilder() to construct.
    private UpdateRegion(Builder builder) {
      super(builder);
    }
    private UpdateRegion(boolean noInit) {}
    
    private static final UpdateRegion defaultInstance;
    public static UpdateRegion getDefaultInstance() {
      return defaultInstance;
    }
    
    public UpdateRegion getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_fieldAccessorTable;
    }
    
    private void initFields() {
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;
      
      memoizedIsInitialized = 1;
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }
    
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegionOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_descriptor;
      }
      
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_fieldAccessorTable;
      }
      
      // Construct using org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }
      
      private Builder(BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }
      
      public Builder clear() {
        super.clear();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.getDescriptor();
      }
      
      public org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion getDefaultInstanceForType() {
        return org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.getDefaultInstance();
      }
      
      public org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion build() {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }
      
      private org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return result;
      }
      
      public org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion buildPartial() {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion result = new org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion(this);
        onBuilt();
        return result;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion) {
          return mergeFrom((org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion other) {
        if (other == org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.getDefaultInstance()) return this;
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public final boolean isInitialized() {
        return true;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              onChanged();
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                onChanged();
                return this;
              }
              break;
            }
          }
        }
      }
      
      
      // @@protoc_insertion_point(builder_scope:org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegion)
    }
    
    static {
      defaultInstance = new UpdateRegion(true);
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegion)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n)messages/region/UpdateRegionMessage.pr" +
      "oto\022?org.haw.cas.Adapters.AkkaAdapter.Im" +
      "plementation.ProtoMsgBuilder\"\016\n\014UpdateRe" +
      "gion"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_org_haw_cas_Adapters_AkkaAdapter_Implementation_ProtoMsgBuilder_UpdateRegion_descriptor,
              new java.lang.String[] { },
              org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.class,
              org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateRegionMessage.UpdateRegion.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }
  
  // @@protoc_insertion_point(outer_class_scope)
}