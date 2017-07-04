class CreateUsers < ActiveRecord::Migration[5.0]
  def up
    create_table :users do |t|
      t.string "first_name", :limit => 25
      t.string "last_name", :limit => 75
      t.string "email", :default => '', :null => false
      t.string "username", :limit => 25
      t.string "password_digest"
      t.string "remember_digest"
      t.string "gender"
      t.string "phone_number", :limit => 10
      t.integer "age"
      t.integer "points", :default => 0
      t.boolean "isCoach", :default => false
      t.boolean "admin", :default => false

      t.string "activation_digest"
      t.boolean "activated", :default => false
      t.datetime "activated_at"

      t.string "authentication_token"

      t.timestamps
    end
  end

  def down
    drop_table :users
  end
end
