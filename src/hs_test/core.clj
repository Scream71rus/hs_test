(ns hs-test.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hs-test.handlers.patient :refer [patient]]
            [ring.middleware.json :as middleware]
            [hs-test.db :refer [db-connect]]
            [environ.core :refer [env]]))

(db-connect)

(defroutes app-routes
  (-> (routes patient
      (route/not-found "Not Found"))))

(defn make-app-test []
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(defn make-app []
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(def app
  (if (= (env :env) "test") (make-app-test) (make-app)))
