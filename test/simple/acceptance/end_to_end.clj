(ns acceptance.end-to-end
  (:require [clj-webdriver.taxi :as taxi]
            [midje.sweet :refer :all]
            [rs.web :as web]
            [ring.adapter.jetty :as jetty]))

(def server (atom nil))

(defn start-server []
  (when @server
    (throw (Exception. "Server started twice!")))
  (reset! server (jetty/run-jetty web/app {:port 3001 :join? false})))

(defn stop-server []
  (when-not @server
    (throw (Exception. "Server stopped when not started!")))
  (.stop @server)
  (reset! server nil))

(fact "can view all the things"
      (start-server)
      ; clj-webdriver taxi lives around a global browser
      ; TODO: maybe use core webdriver so we can be a bit cleaner?
      (taxi/set-driver! {:browser :firefox})
      (taxi/to "http://localhost:3001/index.html")
      (taxi/text "section>h1") => "Things!"
      (taxi/close)
      (stop-server))