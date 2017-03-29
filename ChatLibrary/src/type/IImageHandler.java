package type;

public interface IImageHandler {

	boolean loadImage(String text);

	byte[] getImageAsBytes();

	int getImageWidth();

	int getImageHeight();

}
