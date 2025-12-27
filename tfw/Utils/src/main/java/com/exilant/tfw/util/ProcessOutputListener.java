package com.exilant.tfw.util;

public abstract interface ProcessOutputListener
{
  public abstract void outStream(String paramString);

  public abstract void errorStream(String paramString);
}