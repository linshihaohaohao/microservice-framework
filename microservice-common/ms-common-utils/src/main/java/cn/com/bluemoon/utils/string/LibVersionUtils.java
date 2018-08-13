package cn.com.bluemoon.utils.string;

/**
 * 版本比较
 * 
 * @author niejian
 *
 */
public class LibVersionUtils {

	private static class Version {
		// Assume version string read as majorVersion.minorVersion.revision or
		// majorVersion.minorVersion
		private int majorVersion = 0;
		private int minorVersion = 0;
		private int revision = 0;

		public Version(String versionString) {
			int lastDotPos = 0;
			int nextDotPos = 0;
			nextDotPos = versionString.indexOf(".");
			if (nextDotPos >= 0) {
				try {
					majorVersion = Integer.parseInt(versionString.substring(0,
							nextDotPos));
				} catch (Exception e) {
				}
				lastDotPos = nextDotPos;
				nextDotPos = versionString.indexOf(".", nextDotPos + 1);
				if (nextDotPos >= 0) {
					try {
						minorVersion = Integer.parseInt(versionString
								.substring(lastDotPos + 1, nextDotPos).trim());
					} catch (Exception e) {
					}
					try {
						revision = Integer.parseInt(versionString.substring(
								nextDotPos + 1).trim());
					} catch (Exception e) {
					}
				} else {
					try {
						minorVersion = Integer.parseInt(versionString
								.substring(lastDotPos + 1).trim());
					} catch (Exception e) {
					}
				}
			} else {
				try {
					majorVersion = Integer.parseInt(versionString.trim());
				} catch (Exception e) {
				}
			}
			// if (!Constants.release)
			// Log.d("test", "versionString = " + versionString + ", version = "
			// + majorVersion + "." + minorVersion + "." + revision);
		}

		public boolean isNewerMajorVersion(Version testVersion) {
			return (testVersion.majorVersion > majorVersion);
		}

		public boolean isNewerMinorVersion(Version testVersion) {
			return (testVersion.majorVersion == majorVersion && testVersion.minorVersion > minorVersion);
		}

		public boolean isNewerRevision(Version testVersion) {
			return (testVersion.majorVersion == majorVersion
					&& testVersion.minorVersion == minorVersion && testVersion.revision > revision);
		}
	}

	/**
	 * Compare if version2String is a newer major version than version1String
	 * 
	 * @param version1String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @param version2String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @return true if the major version represented by version2String is newer
	 *         than that of version1String, false otherwise
	 */
	public static boolean isNewerMajorVersion(String version1String,
			String version2String) {
		return (new Version(version1String)).isNewerMajorVersion(new Version(
				version2String));
	}

	/**
	 * Compare if version2String is a newer minor version than version1String
	 * 
	 * @param version1String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @param version2String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @return true if the minor version represented by version2String is newer
	 *         than that of version1String, false otherwise
	 */
	public static boolean isNewerMinorVersion(String version1String,
			String version2String) {
		return (new Version(version1String)).isNewerMinorVersion(new Version(
				version2String));
	}

	/**
	 * Compare if version2String is a newer sub version than version1String
	 * 
	 * @param version1String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @param version2String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @return true if the sub version represented by version2String is newer
	 *         than that of version1String, false otherwise
	 */
	public static boolean isNewerRevision(String version1String,
			String version2String) {
		return (new Version(version1String)).isNewerRevision(new Version(
				version2String));
	}

	/**
	 * Compare if version2String is a newer version than version1String
	 * 
	 * @param version1String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @param version2String
	 *            (in the form of "majorVersion.minorVersion.revision" or
	 *            "majorVersion.minorVersion")
	 * @return true if the version represented by version2String is newer than
	 *         that of version1String, false otherwise
	 */
	public static boolean isNewerVersion(String version1String,
			String version2String) {
		Version version1 = new Version(version1String);
		Version version2 = new Version(version2String);

		return (version1.isNewerMajorVersion(version2)
				|| version1.isNewerMinorVersion(version2) || version1
					.isNewerRevision(version2));
	}

	public static void main(String[] args) {
		System.out.println(isNewerVersion("1.4.10", "1.3.9"));
	}
}
