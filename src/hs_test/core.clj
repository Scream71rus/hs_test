(ns hs-test.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hs-test.handlers.patient :refer [patient]]
            [ring.middleware.json :as middleware]
            [ring.middleware.cors :refer [wrap-cors]]
            [environ.core :refer [env]]
            [hs-test.db :as db]))

(defroutes app-routes
  (-> (routes patient
      (route/not-found "Not Found"))))

(defn make-app-test []
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(defn make-app []
  (db/make-migrations)
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response
      (wrap-cors :access-control-allow-origin #".*" :access-control-allow-methods [:get :put :post :delete :options])
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(def app
  (if (= (env :env) "test") (make-app-test) (make-app)))

; lein ring server-headless
; lein repl
