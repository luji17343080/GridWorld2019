import imagereader.Runner;

public class ImageProcessorRunner {
	private ImageProcessorRunner() {}
	/**
     * @param args
     */	 
	public static void main(String[] args){  
		ImplementImageIO imageioer = new ImplementImageIO();  
		ImplementImageProcessor processor = new ImplementImageProcessor();  
		Runner.run(imageioer, processor);     
	}
}
