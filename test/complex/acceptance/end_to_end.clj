(ns acceptance.end-to-end
  (:require [clj-webdriver.taxi :as taxi]
            [midje.sweet :refer :all]
            [rs.system :as system]
            [ring.adapter.jetty :as jetty]
            [com.stuartsierra.component :as component]))

(def system (atom nil))

(def e2e-config
  {
    :webserver {:host "localhost" :port 3001}
    :mongo     {:db "rest-sample" :host "localhost", :port 27017}})

(defn start-system []
  (when @system
    (throw (Exception. "System started twice!")))
  (reset! system (system/system e2e-config))
  (swap! system component/start))

(defn stop-system []
  (when-not @system
    (throw (Exception. "System stopped when not started!")))
  (swap! system component/stop)
  (reset! system nil)) ; TODO: can we remove the nil value and checks?

(fact "can view all the things"
      (start-system)
      ; clj-webdriver taxi lives around a global browser
      ; TODO: maybe use core webdriver so we can be a bit cleaner?
      (taxi/set-driver! {:browser :firefox})
      (taxi/to "http://localhost:3001/index.html")
      (taxi/text "section>h1") => "Things!"
      ; TODO: actually set up data and test the output!!!
      (taxi/close)
      (stop-system))