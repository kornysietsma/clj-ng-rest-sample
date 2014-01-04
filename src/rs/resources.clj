(ns rs.resources
  (:require [liberator.core :refer [defresource]]
            [rs.domain :as domain]))

(defresource healthcheck [domain]
  :available-media-types ["application/json"]
  :handle-ok {:status :ok})

(defresource things [domain]
  :available-media-types ["application/json"]
  :allowed-methods [:get :post]
  :handle-ok (domain/things domain)
  :respond-with-entity? true
  :post! (fn [context]
           (let [new-thing (domain/create-thing domain (get-in context [:request :body]))]
             {::id (:_id new-thing)}))
  ; NOTE: should really use absolute url:
  :post-redirect?  (fn [ctx] {:location (format "/thing/%s" (::id ctx))}))

(defresource thing [domain id]
  :available-media-types ["application/json"]
  :allowed-methods [:get :put]
  :exists? (fn [context]
             (let [thing (domain/thing domain id)]
               (when-not (nil? thing)
                 {::thing thing})))
  :handle-ok ::thing
  :handle-created ::thing
  :can-put-to-missing? false
  :put! (fn [context]
          {::thing (domain/update-thing domain (get-in context [:request :body]))}))
