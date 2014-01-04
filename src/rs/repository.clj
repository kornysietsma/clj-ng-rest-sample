(ns rs.repository
  (:import (org.bson.types ObjectId))
  (:require [somnium.congomongo :as m]
            [com.stuartsierra.component :as component]))

(defrecord Repository [host port db connection]
  component/Lifecycle
  (start [this]
    (println "--> starting db connection")
    (let [conn (m/make-connection db :host host :port port)]
      (assoc this :connection conn)))
  (stop [this]
    (println "--> stopping db connection")
    (m/close-connection (:connection this))
    this))

(defn new-repository [host port db]
  (map->Repository {:host host :port port :db db}))

(defn- to-object-id [s] (ObjectId. s))
(defn- from-object-id [o] (.toString o))

(defn- to-db "convert map to mongo representation with object id"
  [m]
  (let [id (:_id m)]
    (if id
      (assoc m :_id (to-object-id id))
      m)))

(defn- from-db "convert db map to map with string id"
  [m]
  (let [id (:_id m)]
    (if id
      (assoc m :_id (from-object-id id))
      m)))

(defn things "list all matching things"
  ([repo] (things repo {}))
  ([repo query]
   (m/with-mongo (:connection repo)
                 (map from-db
                      (m/fetch :things :where query)))))

(defn thing "get a single thing"
  [repo id]
  (m/with-mongo (:connection repo)
                (->> id
                     to-object-id
                     (m/fetch-by-id :things)
                     from-db)))

(defn create-thing [repo t]
  (m/with-mongo (:connection repo)
                (->> t
                     to-db
                     (m/insert! :things)
                     from-db)))

(defn update-thing [repo new-t]
  (m/with-mongo (:connection repo)
                (let [id (to-object-id (:_id new-t))]
                  (->> new-t
                       to-db
                       (m/fetch-and-modify :things {:_id id})
                       from-db))))



