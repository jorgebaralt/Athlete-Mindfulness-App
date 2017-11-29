FactoryGirl.define do
  factory :user do
    # title { Faker::Lorem.word }
    # created_by { Faker::Number.number(10) }
    first_name { Faker::Lorem.word}
    last_name { Faker::Lorem.word}
    email { Faker::Lorem.word}
    encrypted_password { Faker::Lorem.word}
    phone { Faker::Lorem.word}
    gender { Faker::Number.number(1) }
    points { Faker::Number.number(3) }
    age { Faker::Number.number(2) }

  end
end