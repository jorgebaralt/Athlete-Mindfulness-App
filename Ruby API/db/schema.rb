# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20171125231140) do

  # These are extensions that must be enabled in order to support this database
  enable_extension "plpgsql"

  create_table "answers", force: :cascade do |t|
    t.text     "answer"
    t.integer  "points"
    t.integer  "user_id"
    t.integer  "question_id"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
    t.index ["question_id"], name: "index_answers_on_question_id", using: :btree
    t.index ["user_id"], name: "index_answers_on_player_id", using: :btree
  end

  create_table "notifications", force: :cascade do |t|
    t.text     "message"
    t.integer  "question_id"
    t.text     "url"
    t.datetime "created_at",  null: false
    t.datetime "updated_at",  null: false
    t.index ["question_id"], name: "index_notifications_on_question_id", using: :btree
  end

  create_table "players_notifications", id: :integer, default: -> { "nextval('player_notifications_id_seq'::regclass)" }, force: :cascade do |t|
    t.integer  "notification_id"
    t.integer  "user_id"
    t.integer  "counter",         default: 0
    t.integer  "received",        default: 0, null: false
    t.datetime "created_at",                  null: false
    t.datetime "updated_at",                  null: false
    t.index ["notification_id"], name: "index_players_notifications_on_notification_id", using: :btree
    t.index ["user_id"], name: "index_players_notifications_on_player_id", using: :btree
  end

  create_table "questions", force: :cascade do |t|
    t.integer  "category",                   null: false
    t.integer  "age_range",                  null: false
    t.integer  "gender",                     null: false
    t.integer  "question_type",              null: false
    t.text     "options"
    t.text     "question_text",              null: false
    t.integer  "coach_id"
    t.integer  "player_id"
    t.integer  "pattern_multi",  default: 0, null: false
    t.datetime "created_at",                 null: false
    t.datetime "updated_at",                 null: false
    t.integer  "question_order"
    t.index ["player_id"], name: "index_questions_on_player_id", using: :btree
  end

  create_table "users", force: :cascade do |t|
    t.string   "email",                              null: false
    t.string   "encrypted_password",                 null: false
    t.string   "first_name",                         null: false
    t.string   "last_name",                          null: false
    t.string   "phone"
    t.integer  "gender"
    t.integer  "age"
    t.integer  "coach_id"
    t.string   "coach_name"
    t.integer  "is_coach"
    t.integer  "points",                 default: 0
    t.integer  "age_range"
    t.string   "auth_token"
    t.string   "reset_password_token"
    t.datetime "reset_password_sent_at"
    t.datetime "remember_created_at"
    t.integer  "sign_in_count"
    t.datetime "current_sign_in_at"
    t.datetime "last_sign_in_at"
    t.datetime "created_at",                         null: false
    t.datetime "updated_at",                         null: false
    t.string   "device_id"
    t.index ["email"], name: "index_users_on_email", unique: true, using: :btree
    t.index ["reset_password_token"], name: "index_users_on_reset_password_token", unique: true, using: :btree
  end

  add_foreign_key "answers", "questions", name: "answers_questions_id_fk"
  add_foreign_key "answers", "users", name: "answers_users_id_fk"
end
