(ns rs.repository.thing-repository
  (:import (org.bson.types ObjectId))
  (:require [somnium.congomongo :as m]))

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
  ([db] (things db {}))
  ([db query]
   (m/with-mongo (:connection db)
                 (map from-db
                      (m/fetch :things :where query)))))

(defn thing "get a single thing"
  [db id]
  (m/with-mongo (:connection db)
                (->> id
                     to-object-id
                     (m/fetch-by-id :things)
                     from-db)))

(defn create-thing [db t]
  (m/with-mongo (:connection db)
                (->> t
                     to-db
                     (m/insert! :things)
                     from-db)))

(defn update-thing [db new-t]
  (m/with-mongo (:connection db)
                (let [id (to-object-id (:_id new-t))]
                  (->> new-t
                       to-db
                       (m/fetch-and-modify :things {:_id id})
                       from-db))))



