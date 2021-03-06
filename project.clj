(defproject clj-ng-rest-sample "0.2.1"
  :description "sample clojure REST api with angular front end"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [midje "1.6.0"]
                 [ring "1.2.1"]
                 [ring/ring-json "0.2.0"]
                 [compojure "1.1.6" :exclusions [org.clojure/core.incubator]]
                 [liberator "0.10.0"]
                 [hiccup "1.0.4"]
                 [cheshire "5.3.0"]
                 [clj-time "0.6.0"]
                 [congomongo "0.4.1"]
                 [clj-webdriver "0.6.0"]
                 [com.stuartsierra/component "0.2.1"]]

  :plugins [[lein-ring "0.8.2"]
            [lein-midje "3.1.1"]
            [lein-haml-sass "0.2.7-SNAPSHOT"]
            [lein-pprint "1.1.1"]]

  :scss {:src              "resources/scss"
         :output-directory "resources/public/css"
         :output-extension "css"}

  ; TODO - re-enable hooks if/when we can get the scss compiler to be less noisy
  ;:hooks [leiningen.scss]

  :aliases {"unit"        ["midje" "unit.*"]
            "integration" ["midje" "int.*"]
            "e2e"         ["midje" "e2e.*"]
            "simple"      ["with-profile" "simple," "ring" "server-headless"]
            "complex"     ["with-profile" "complex" "run"]}

  ; defaults will be merged with profiles
  :source-paths ["src/shared"]
  :test-paths []

  :profiles {:shared
              {:dependencies [[slamhound "1.5.0"]
                              [ring-mock "0.1.5"]]}
             :simple
              [:shared
               {:source-paths ["src/simple"]
                :test-paths   ["test/simple"]
                ; "lein ring" runs the simple app
                :ring         {:handler rs.web/app}
                }]
             :complex
              [:shared
               {:source-paths ["src/complex"]
                :test-paths   ["test/complex"]
                ; "lein run" runs the complex app
                :main         rs.system
                }]
             :dev
              [:complex {}] ; default to complex
             })
  
