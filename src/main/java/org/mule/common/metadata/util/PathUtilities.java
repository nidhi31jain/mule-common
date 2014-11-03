package org.mule.common.metadata.util;

public final class PathUtilities
{
  private static final String WILDCARD = "*";


  /**
   * Get the parent of the given path.
   *
   * @param path The path for which to retrieve the parent
   *
   * @return The parent path. /sub/sub2/index.html -> /sub/sub2 If the given path is the root path ("/" or ""), return a blank string.
   */
  public static String extractDirectoryPath(String path)
  {
    if ((path == null) || path.equals("") || path.equals("/"))
    {
      return "";
    }

    int lastSlashPos = path.lastIndexOf('/');

    if (lastSlashPos >= 0)
    {
      return path.substring(0, lastSlashPos); //strip off the slash
    }
    else
    {
      return ""; //we expect people to add  + "/somedir on their own
    }
  }
  
}