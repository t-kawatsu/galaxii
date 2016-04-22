package com.galaxii.common.util;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.Info;

/**
 * http://books.google.co.jp/books?id=SKJxWg_uKJUC&pg=PA60&lpg=PA60&dq=imagemagick+%E5%9B%BA%E5%AE%9A%E5%B9%85%E3%80%80%E4%BD%99%E7%99%BD&source=bl&ots=efIl-8qfih&sig=tsuH1Pvz1wQXPVnxyok5MG6VIw4&hl=ja&sa=X&ei=0vpFUY2oKJDvmAXoyYFw&ved=0CEkQ6AEwAw#v=onepage&q=imagemagick%20%E5%9B%BA%E5%AE%9A%E5%B9%85%E3%80%80%E4%BD%99%E7%99%BD&f=false
 * @author t-kawatsu
 *
 */
public class ImageUtil {

	private ConvertCmd cmd = new ConvertCmd();

	public void resize(File base, File dest, int w, int h) 
		throws IOException, InterruptedException, Exception {
		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		// add base image
		op.addImage();
		// resize
		op.resize(w, h);
		// make opaque to white 
		op.addRawArgs("-background", "white");
		op.addRawArgs("-flatten");
		// fix image size
		op.addRawArgs("-gravity", "center");
		op.extent(w, h);
		// add dest image
		op.addImage();
		// execute the operation
		cmd.run(op, base.toString(), dest.toString());
	}
	
	public void trimResize(File base, File dest, int w, int h) 
		throws IOException, InterruptedException, Exception {
		
		Info info = new Info(base.toString(),true);
		int bw = info.getImageWidth();
		int bh = info.getImageHeight();
		int dw = bw - w;
		int dh = bh - h;
		
		// create the operation, add images and operators/options
		IMOperation op = new IMOperation();
		// add base image
		op.addImage();
		// resize
		if(dw < dh) {
			op.resize(w, null);
		} else {
			op.resize(null, h);
		}
		
		// make opaque to white 
		op.background("white");
		op.flatten();
		
		// fix image size
		op.gravity("center");
		op.extent(w, h);
		
		// crop
		if(dw < dh) {
			op.crop(w, h, 0, 0);
		} else {
			op.crop(w, h, 0, 0);
		}
		// +repage
		op.p_repage();
		// add dest image
		op.addImage();
		// execute the operation
		cmd.run(op, base.toString(), dest.toString());
	}
}
