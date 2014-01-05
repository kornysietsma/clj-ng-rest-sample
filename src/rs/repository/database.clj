(ns rs.repository.database
  (:require [somnium.congomongo :as m]
            [com.stuartsierra.component :as component]))

; really just handles lifecycle, and holds a db connection
(defrecord Database [host port db connection]
  component/Lifecycle
  (start [this]
    (println "--> starting db connection")
    (let [conn (m/make-connection db :host host :port port)]
      (assoc this :connection conn)))
  (stop [this]
    (println "--> stopping db connection")
    (m/close-connection (:connection this))
    this))

(defn new-database [host port db]
  (map->Database {:host host :port port :db db}))

