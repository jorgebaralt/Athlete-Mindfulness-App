class CreateUsers < ActiveRecord::Migration[5.0]
  def up
    create_table :users do |t|
      t.string "username", :limit => 25
      t.string "first_name", :limit => 25
      t.string "last_name", :limit => 75
      t.string "gender"
      t.string "email", :default => '', :null => false
      t.integer "phone_number"
      t.integer "age"
      t.integer "points", :default => 0
      t.string "coach", :default => '', :null => false
      t.boolean "isCoach", :default => false
      t.string :password_digest
      t.timestamps
    end
  end

  def down
    drop_table :users
  end
end
