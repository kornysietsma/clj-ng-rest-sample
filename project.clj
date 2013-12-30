(defproject clj-ng-rest-sample "0.0.1-SNAPSHOT"
  :description "sample clojure REST api with angular front end"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [midje "1.6.0"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.6"]
                 [liberator "0.10.0"]
                 [hiccup "1.0.4"]
                 [cheshire "5.3.0"]
                 [clj-time "0.6.0"]
                 [congomongo "0.4.1"]
                 [com.stuartsierra/component "0.2.1"]]

  :plugins [[lein-ring "0.8.2"]
            [lein-midje "3.1.1"]
            [lein-haml-sass "0.2.7-SNAPSHOT"]]

  :ring {:handler rs.web/app
         :init rs.web/init
         :destroy rs.web/destroy }

  :scss {:src "resources/scss"
         :output-directory "resources/public/css"
         :output-extension "css"}

  :hooks [leiningen.scss]

  :profiles {:dev {:dependencies [[slamhound "1.5.0"]]}})
  
