(defproject rest-sample "0.0.1-SNAPSHOT"
  :description "sample REST api for templatization"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [midje "1.6.0"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.6"]
                 [liberator "0.10.0"]
                 [hiccup "1.0.4"]
                 [cheshire "5.3.0"]
                 [clj-time "0.6.0"]
                 [com.novemberain/monger "1.7.0-beta1"]]
  ;; also need:
  ;;  config
  ;;  logging
  ;;  schema?
  ;;  component or graph or something

  :plugins [[lein-ring "0.8.2"]
            [lein-midje "3.1.1"]]

  :ring {:handler rs.web/app
         :init rs.web/init
         :destroy rs.web/destroy }

  :profiles {:dev {:dependencies [[slamhound "1.5.0"]]}})
  
