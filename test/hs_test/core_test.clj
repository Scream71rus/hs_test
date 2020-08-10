(ns hs-test.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [hs-test.core :refer :all]))

(deftest test-app
  (testing "create patient"
    (let [response (app (-> (mock/request :post "/patient")
                            (mock/json-body {:first-name        "first-name@qwe.ru"
                                             :last-name         "last-name"
                                             :gender            "MALE"
                                             :birthday          "1999-10-10"
                                             :medical-insurance "medical-insurance"})))]
      (is (= (:status response) 200))))

  (testing "create patient failed"
    (let [data {:first-name        111
                :last-name         111
                :gender            111
                :birthday          111
                :medical-insurance 111}
          response (app (-> (mock/request :post "/patient")
                            (mock/json-body data)))]

      (is (= (:status response) 400))

      (is (= (->> (get-in response [:body :errors])
                  keys
                  (map keyword))
             (keys data)))

      (is (= (get-in response [:body :message]) "validation error"))))

  (testing "update patient"
    (let [response (app (-> (mock/request :put "/patient/1")
                            (mock/json-body {:first-name        "first-name@qwe.ru"
                                             :last-name         "last-name"
                                             :gender            "MALE"
                                             :birthday          "1999-10-10"
                                             :medical-insurance "medical-insurance"})))]
      (is (= (:status response) 200))))

  (testing "update patient failed"
    (let [data {:first-name        111
                :last-name         111
                :gender            111
                :birthday          111
                :medical-insurance 111}
          response (app (-> (mock/request :put "/patient/1")
                            (mock/json-body data)))]

      (is (= (:status response) 400))

      (is (= (->> (get-in response [:body :errors])
                  keys
                  (map keyword))
             (keys data)))

      (is (= (get-in response [:body :message]) "validation error"))))

  (testing "delete patient"
    (let [response (app (-> (mock/request :delete "/patient/1")))]
      (is (= (:status response) 200))))

  (testing "get all patients"
    (let [response (app (mock/request :get "/patient"))]
      (is (= (:status response) 200)))))