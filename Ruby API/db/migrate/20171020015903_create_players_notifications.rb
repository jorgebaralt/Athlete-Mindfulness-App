class CreatePlayersNotifications < ActiveRecord::Migration[5.0]
  def change
    create_table :players_notifications do |t|
      t.integer "notification_id"
      t.integer "user_id"
    	t.integer "counter", :default => 0
    	t.integer "received", :null => false, :default => false
	    t.belongs_to :notification
	    t.belongs_to :user
      t.timestamps
    end
  end
end
