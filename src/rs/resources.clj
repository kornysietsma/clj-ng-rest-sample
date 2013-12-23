(ns rs.resources
  (:require [liberator.core :refer [defresource]]
            [rs.repository :as repository]))

(defresource healthcheck
  :available-media-types ["application/json"]
  :handle-ok {:status :ok})

(defresource things
  :available-media-types ["application/json"]
  :allowed-methods [:get :post]
  :handle-ok (repository/things)
  :respond-with-entity? true
  :post! (fn [context]
           (let [new-thing (repository/create-thing (get-in context [:request :body]))]
             {::id (:_id new-thing)}))
  ; NOTE: should really use absolute url:
  :post-redirect?  (fn [ctx] {:location (format "/thing/%s" (::id ctx))}))

(defresource thing [id]
  :available-media-types ["application/json"]
  :allowed-methods [:get :put]
  :exists? (fn [context]
             (let [thing (repository/thing id)]
               (when-not (nil? thing)
                 {::thing thing})))
  :handle-ok ::thing
  :handle-created ::thing
  :can-put-to-missing? false
  :put! (fn [context]
          {::thing (repository/update-thing (get-in context [:request :body]))}))
