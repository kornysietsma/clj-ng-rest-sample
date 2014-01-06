(ns rs.complex.resources
  (:require [liberator.core :refer [defresource]]
            [rs.complex.domain.things :as things]))

(defresource healthcheck [domain]
  :available-media-types ["application/json"]
  :handle-ok {:status :ok})

(defresource things [domain]
  :available-media-types ["application/json"]
  :allowed-methods [:get :post]
  :handle-ok (things/things domain)
  :respond-with-entity? true
  :post! (fn [context]
           (let [new-thing (things/create-thing domain (get-in context [:request :body]))]
             {::id (:_id new-thing)}))
  ; NOTE: should really use absolute url:
  :post-redirect?  (fn [ctx] {:location (format "/thing/%s" (::id ctx))}))

(defresource thing [domain id]
  :available-media-types ["application/json"]
  :allowed-methods [:get :put]
  :exists? (fn [context]
             (let [thing (things/thing domain id)]
               (when-not (nil? thing)
                 {::thing thing})))
  :handle-ok ::thing
  :handle-created ::thing
  :can-put-to-missing? false
  :put! (fn [context]
          {::thing (things/update-thing domain (get-in context [:request :body]))}))
