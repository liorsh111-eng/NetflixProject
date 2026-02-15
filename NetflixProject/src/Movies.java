public class Movies {
        String title;
        double action;
        double comedy;
        double emotion;
        double thriller;
        double horror;
        double cognitiveLoad;
        double movieLength;

        public Movies(String title, double action, double comedy, double emotion,
                     double thriller, double horror, double cognitiveLoad, double movieLength) {
            this.title = title;
            this.action = action;
            this.comedy = comedy;
            this.emotion = emotion;
            this.thriller = thriller;
            this.horror = horror;
            this.cognitiveLoad = cognitiveLoad;
            this.movieLength = movieLength;
        }

        public String getTitle(){return this.title;}

        public double[] toVector(){
            return new double[]{action,comedy,emotion,thriller,horror,cognitiveLoad,movieLength};
        }

        @Override
        public String toString() {
            return title;
        }
}
