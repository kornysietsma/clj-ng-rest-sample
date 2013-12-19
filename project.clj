(defproject rest-sample "0.0.1-SNAPSHOT"
  :description "sample REST api for templatization"
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]
                                  [ring "1.2.1"]
                                  [compojure "1.1.6"]
                                  [liberator "0.10.0"]
                                  [cheshire "5.3.0"]]}})
  
