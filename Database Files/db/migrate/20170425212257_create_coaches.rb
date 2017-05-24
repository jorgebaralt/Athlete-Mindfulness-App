class CreateCoaches < ActiveRecord::Migration[5.0]
  def up
    create_table :coaches do |t|
      t.string "players"
      t.timestamps
    end
  end

  def down
    drop_table :coaches
  end
end
