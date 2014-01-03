(ns e2e.end-to-end
  (:require [clj-webdriver.taxi :as taxi]
            [midje.sweet :refer :all]
            [rs.web :as web]
            [ring.adapter.jetty :as jetty]))

(def server (atom nil))
(defn start-server []
  (when @server
    (throw (Exception. "Server started twice!")))
  (web/init)
  (reset! server (jetty/run-jetty web/app {:port 3001 :join? false})))

(defn needs-server []
  (when-not @server (start-server)))

(defn stop-server []
  (when-not @server
    (throw (Exception. "Server stopped when not started!")))
  (.stop @server)
  (web/destroy)
  (reset! server nil))

(fact "can view all the things"
  (needs-server)
  (taxi/set-driver! {:browser :firefox} "http://localhost:3001/index.html")
  ;(taxi/to "http://localhost:3001/index.html")
  (taxi/text "section>h1") => "Things!"
  (taxi/close)
  (stop-server))