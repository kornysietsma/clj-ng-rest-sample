(ns rs.domain
  (:require [rs.repository :as repository]))

;; Domain currently is basically a featureless wrapper around the repository
;;  of course in a real system it should encapsulate the actual domain logic!

; Note: Domain is stateless so doesn't need to implement component/LifeCycle
(defrecord Domain [config repository])

(defn new-domain [config]
  (->Domain config nil))

(defn things "list all matching things"
  ([domain] (things domain {}))
  ([domain query] (repository/things (:repository domain))))

(defn thing "get a single thing"
  [domain id]
  (repository/thing (:repository domain) id))

(defn create-thing [domain t]
  (repository/create-thing (:repository domain) t))

(defn update-thing [domain new-t]
  (repository/update-thing (:repository domain) new-t))