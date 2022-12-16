package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class NewtonParallel {
	

	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int workers = getWorkers(args);
		int tracks = getTracks(args);
		
		System.out.println("Welcome to Newton-Raphson parallel-based fractal viewer.\n" +
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		
		List<Complex> listC = new ArrayList<>();
		int ind = 1;
		String input = "";
		do {
			System.out.print("Root " + ind++ + "> ");
			input = scanner.nextLine().trim();
			if(input.equals("done"))
				break;
			
			listC.add(Newton.stringToComplex(input));
				
		} while(true);
		scanner.close();
			
		if(listC.size() < 2) {
			System.out.println("Needed at least 2 roots!");
			return;
		}
		
		
		Complex[] arrayC = new Complex[listC.size()];
		for(int i=0; i<listC.size(); i++)
			arrayC[i] = listC.get(i);
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(new Complex(2, 0), arrayC);
		System.out.println(crp.toString());
		FractalViewer.show(new MyProducer(crp, workers, tracks));
		
		
		
	}
	


	private static int getWorkers(String[] args) {
		int numOfWorkers = Runtime.getRuntime().availableProcessors();
		
		boolean alreadySet = false;
		
		for(int i=0; i<args.length; i++) {
			String arg = args[i].strip();
			
			if(arg.startsWith("--w")) {
				if(alreadySet)
					throw new IllegalArgumentException("Already got number of workers!");
				
				int indOfEquals = arg.indexOf('=');	
				numOfWorkers = Integer.parseInt(arg.substring(indOfEquals+1));
			
			} else if(arg.equals("-w")) {
				if(alreadySet)
					throw new IllegalArgumentException("Already got number of workers!");
				
				numOfWorkers = Integer.parseInt(args[i+1]);
			}
		}
	
		return numOfWorkers;
	}
	
	private static int getTracks(String[] args) {
		int numOfTracks = Runtime.getRuntime().availableProcessors() * 4;
		
		boolean alreadySet = false;
		
		for(int i=0; i<args.length; i++) {
			String arg = args[i].strip();
			
			if(arg.startsWith("--t")) {
				if(alreadySet)
					throw new IllegalArgumentException("Already got number of tracks!");
				
				int indOfEquals = arg.indexOf('=');	
				numOfTracks = Integer.parseInt(arg.substring(indOfEquals+1));
				alreadySet = true;
			
			} else if(arg.equals("-t")) {
				if(alreadySet)
					throw new IllegalArgumentException("Already got number of tracks!");
				
				numOfTracks = Integer.parseInt(args[i+1]);
				alreadySet = true;
			}
		}
		
		if(numOfTracks < 1)
			return 1;
	
		return numOfTracks;
	}
	
	
	public static class CalculatingJob implements Runnable {
		ComplexRootedPolynomial crp;
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int maxIter;
		short[] data;
		AtomicBoolean cancel;
		double rootTreshold = 0.002;
		double convergenceTreshold = 0.001;
		public static CalculatingJob NO_JOB = new CalculatingJob();
		
		private CalculatingJob() {
		}
		
		public CalculatingJob(ComplexRootedPolynomial crp, double reMin, double reMax, double imMin,
				double imMax, int width, int height, int yMin, int yMax, 
				int m, short[] data, AtomicBoolean cancel) {
			super();
			this.crp = crp;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.maxIter = m;
			this.data = data;
			this.cancel = cancel;
		}
		
		@Override
		public void run() {
			
			ComplexPolynomial polynom = crp.toComplexPolynom();
			int offset = 0;
			
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					
					double cre = x / (width-1.0) * (reMax - reMin) + reMin;
					double cim = (height-1.0-y) / (height-1) * (imMax - imMin) + imMin;
					Complex c = new Complex(cre, cim);
					Complex zn = c;
					double module;
					int iter = 0;
					do{
						Complex numerator = polynom.apply(zn);
						Complex denominator = polynom.derive().apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						
						iter++;
					} while(module > convergenceTreshold && iter < maxIter);
					
					int index = crp.indexOfClosestRootFor(zn, rootTreshold);
					data[offset++] = (short) (index+1);
				}
			}
			
		}
	}
	
	
	public static class MyProducer implements IFractalProducer {
		
		private ComplexRootedPolynomial crp;
		int workersNum, tracks;
		
		public MyProducer(ComplexRootedPolynomial crp, int workers, int tracks) {
			this.crp = crp;
			this.workersNum = workers;
			this.tracks = tracks;
		}
		
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			
			if(tracks > height)
				tracks = height;
			
			System.out.println("Zapocinjem izracun s " + workersNum + " radnika i " + tracks + " traka...");
			int m = 16*16*16;
			short[] data = new short[width * height];
			final int trackNum = tracks;
			int numYPerTrack = height / trackNum;
			
			final BlockingQueue<CalculatingJob> queue = new LinkedBlockingQueue<>();

			Thread[] workers = new Thread[workersNum];
			for(int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							CalculatingJob p = null;
							try {
								p = queue.take();
								if(p==CalculatingJob.NO_JOB) break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for(int i = 0; i < workers.length; i++) {
				workers[i].start();
			}
			
			for(int i = 0; i < trackNum; i++) {
				int yMin = i*numYPerTrack;
				int yMax = (i+1)*numYPerTrack-1;
				if(i==trackNum-1) {
					yMax = height-1;
				}
				CalculatingJob job = new CalculatingJob(crp, reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, cancel);
				while(true) {
					try {
						queue.put(job);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						queue.put(CalculatingJob.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			for(int i = 0; i < workers.length; i++) {
				while(true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			
			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short)m, requestNo);
		}
	}
	
	
	
	

}
