class CreateQuestions < ActiveRecord::Migration[5.0]
  def change
    create_table :questions do |t|
      t.integer "category", :null => false
      t.integer "age_range", :null => false
      t.integer "gender", :null => false
      t.integer "question_type", :null => false
      t.text "options"
      t.text "question_text", :null => false
      t.integer "coach_id"
      t.integer "player_id"
      t.integer "pattern_multi", :null => false, :default => false
      t.integer "question_order"
	    t.integer "points_matter", :null => false
      t.belongs_to :user
      t.timestamps
    end
  end
end
