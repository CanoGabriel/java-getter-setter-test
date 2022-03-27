package com.example.utils;

public interface ICustomTypeHandler {
  public static final ICustomTypeHandler PRIMITIVE_HANDLER_BYTE = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Byte.valueOf((byte) 1);
    }
  };
  
  public static final ICustomTypeHandler PRIMITIVE_HANDLER_SHORT = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Short.valueOf((short) 1);
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_INT = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Integer.valueOf(1);
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_LONG = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Long.valueOf(1L);
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_FLOAT = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Float.valueOf((float) 1);
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_DOUBLE = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Double.valueOf(1);
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_CHAR = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Character.valueOf('a');
    }
  };

  public static final ICustomTypeHandler PRIMITIVE_HANDLER_BOOLEAN = new ICustomTypeHandler() {
    @Override
    public Object getDefaultInstance() {
      return Boolean.valueOf(true);
    }
  };

  Object getDefaultInstance();
}
