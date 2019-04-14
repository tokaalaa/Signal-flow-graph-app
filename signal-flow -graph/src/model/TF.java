package model;

public class TF{

	public static double calculateTF(int source, int sink) {
		Base base = Base.getInstance(null);
		Loops.calculateLoops();
		Paths.calculatePaths(source, sink);
		Delta.calculateDelta();
		double TF = 0 ;
		for (int i = 1; i < base.getDeltas().length; i++) {
			TF += base.getDeltas()[i] * Paths.pathGain(i-1);
		}
		return TF / base.getDeltas()[0];
	}
}
