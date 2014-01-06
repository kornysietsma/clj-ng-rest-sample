(ns rs.simple.web
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [rs.simple.resources :as resources]
            [rs.pages :as pages]))

(defroutes app-routes
           (GET "/healthcheck" [] resources/healthcheck)
           (ANY "/things" [] resources/things)
           (ANY ["/thing/:id", :id #".+"] [id]
                (resources/thing id))

           (GET "/" [] (pages/index))
           (route/resources "/")
           (route/not-found {:status 404 :body "nothing to see here, move along"})
           )

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-body {:keywords? true})))
