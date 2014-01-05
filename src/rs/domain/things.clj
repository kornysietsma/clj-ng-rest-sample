(ns rs.domain.things
  (:require [rs.repository.thing-repository :as repository]))

(defn things "list all matching things"
  ([domain] (things domain {}))
  ([domain query] (repository/things (:database domain))))

(defn thing "get a single thing"
  [domain id]
  (repository/thing (:database domain) id))

(defn create-thing [domain t]
  (repository/create-thing (:database domain) t))

(defn update-thing [domain new-t]
  (repository/update-thing (:database domain) new-t))