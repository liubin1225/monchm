package com.warchm.common.utils;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {
	
	private long num100Ks = 0;
	@SuppressWarnings("unused")
	private long theBytesRead = 0;
	@SuppressWarnings("unused")
	private long theContentLength = -1;
	@SuppressWarnings("unused")
	private int whichItem = 0;
	private int percentDone = 0;
	private boolean contentLengthKnown = false;

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		// TODO Auto-generated method stub
		if (contentLength > -1) {
			contentLengthKnown = true;
			}
			theBytesRead = bytesRead;
			theContentLength = contentLength;
			whichItem = items;
	
			long nowNum100Ks = bytesRead / 100000;
			if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
			percentDone = (int) Math.round(100.00 * bytesRead / contentLength);
			}
		}
	}
	
	public int getPercentDone() {
	   return percentDone;
	}

}
