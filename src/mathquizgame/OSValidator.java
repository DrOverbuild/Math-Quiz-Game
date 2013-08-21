//
// Code found from 
// http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/
//
// Modified by Jasper Reddin
//
//
//
//

package mathquizgame;

public class OSValidator {
 
	private static String OS = System.getProperty("os.name").toLowerCase();
 
	public static String findOS(){
 
		if (isWindows()) {
			return "windows";
		} else if (isMac()) {
			return "mac";
		} else if (isUnix()) {
			return "unix";
		} else if (isSolaris()) {
			return "solaris";
		} else {
			return "other";
		}
	}
 
	public static boolean isWindows() {
 
		return (OS.indexOf("win") >= 0);
 
	}
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}
 
}