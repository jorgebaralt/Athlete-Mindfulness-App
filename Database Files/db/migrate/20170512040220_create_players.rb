class CreatePlayers < ActiveRecord::Migration[5.0]
  def up
    create_table :players do |t|
      t.string "coach", :default => '', :null => false
      t.timestamps
    end
  end

  def down
  	drop_table :players
  end
end
