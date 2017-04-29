class CreateQuestions < ActiveRecord::Migration[5.0]
  def up
    create_table :questions do |t|
      t.text "question"
      t.text "answer"
      t.string "ageRange" # 10-12, 13-15, 16-18
      t.string "category" # physical, mental, emotional, etc...
      t.timestamps
    end
  end

  def down
    drop_table :questions
  end

end
