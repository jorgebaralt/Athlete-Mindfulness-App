class DeviseCreateUsers < ActiveRecord::Migration[5.0]
  def change
    create_table :users do |t|
      ## Database authenticatable
      t.string "email",              null: false
      t.string "encrypted_password", null: false
      t.string "first_name", :null => false
      t.string "last_name", :null => false
      t.string "phone"
      t.integer "gender"
      t.integer "age"
      t.integer "coach_id"
      t.string "coach_name"
      t.integer "is_coach", :defualt => 0
      t.integer "points", :default => 0
      t.integer "age_range"
      t.string "auth_token"
      t.string "reset_password_token"
      t.datetime "reset_password_sent_at"
      t.datetime "remember_created_at"
      t.integer "sign_in_count"
      t.datetime "current_sign_in_at"
      t.datetime "last_sign_in_at"
      t.timestamps
    end
    add_index :users, :email,                unique: true
    add_index :users, :reset_password_token, unique: true
    # add_index :users, :confirmation_token,   unique: true
    # add_index :users, :unlock_token,         unique: true
  
  end
end
