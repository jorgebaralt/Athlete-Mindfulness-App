class CreateAnswers < ActiveRecord::Migration[5.0]
  def change
    create_table :answers do |t|
      t.text "answer"
    	t.integer "points"
      t.integer "user_id"
      t.integer "question_id"
      t.integer "question_order"
			t.belongs_to :user
			t.belongs_to :question
      t.timestamps
    end
  end
end
