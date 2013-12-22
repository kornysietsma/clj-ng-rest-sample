(ns rs.resources
  (:require [liberator.core :refer [defresource]]
            [rs.repository.db :as db]))

(defresource healthcheck
             :available-media-types ["application/json"]
             :handle-ok {:status :ok})

(defresource things
             :available-media-types ["application/json"]
             :allowed-methods [:get]
             :handle-ok (db/things))
