package com.damienfremont.tool;

import static com.google.common.base.Preconditions.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		checkArgument(args.length > 0, "args are needed");
		String url = getArgUrl(args);
		checkArgument(url != null, "-url arg value must not be null");
		int chapterIndexOverride = getArgChapterIndexOverride(args);
		String target = targetFolder();
		new MainJob().execute(url, target, chapterIndexOverride);
	}

	static String targetFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");
		String ts = sdf.format(new Date());
		String target = String.format("target/%s", ts);
		return target;
	}

	static String getArgUrl(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if ("-url".equals(arg)) {
				int iValue = i + 1;
				checkArgument(args.length > iValue, "-url arg value must not be null");
				String url = args[iValue];
				checkArgument(url != null, "-url arg value must not be null");
				checkArgument(!url.isEmpty(), "-url arg value must not be empty");
				return url;
			}
		}
		return null;
	}

	static int getArgChapterIndexOverride(String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if ("-chapterIndexStartLabel".equals(arg)) {
				int iValue = i + 1;
				String iChapter = args[iValue];
				return Integer.valueOf(iChapter).intValue();
			}
		}
		return 1;
	}
}
