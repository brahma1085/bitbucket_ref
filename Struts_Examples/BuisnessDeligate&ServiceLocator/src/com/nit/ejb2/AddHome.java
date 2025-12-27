// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AddHome.java

package com.nit.ejb2;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

// Referenced classes of package com.nit.ejb2:
//            AddRemote

public interface AddHome
    extends EJBHome
{

    public abstract AddRemote create()
        throws RemoteException, CreateException;
}
