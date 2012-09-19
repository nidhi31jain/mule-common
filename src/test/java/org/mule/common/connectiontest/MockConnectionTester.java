
package org.mule.common.connectiontest;

import org.mule.common.config.XmlDocumentAccessor;
import org.mule.common.config.XmlElement;

import javax.xml.namespace.QName;

public class MockConnectionTester implements ConnectionTester
{

    public boolean supportsConnectionTest(QName element)
    {
        return true;
    }

    public ConnectionTestResult testConnection(XmlElement element, XmlDocumentAccessor callback)
    {
        return new ConnectionTestResult()
        {

            public Status getStatus()
            {
                return ConnectionTestResult.Status.SUCESS;
            }

            public String getMessage()
            {
                return "";
            }
        };
    }

}
