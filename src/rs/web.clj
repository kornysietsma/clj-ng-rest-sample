(ns rs.web
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [rs.resources :as resources]
            [rs.pages :as pages]))

(defroutes app-routes
  (GET "/healthcheck" [] resources/healthcheck)
  (ANY "/things" [] resources/things)
  (ANY ["/thing/:id", :id #".+"] [id]
       (resources/thing id))

  (GET "/" [] (pages/index))
  (route/not-found {:status 404 :body "nothing to see here, move along"})
  (route/resources "/"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-body {:keywords? true})))

(defn init []
  (println "Starting server"))

(defn destroy []
  (println "Stopping server"))
