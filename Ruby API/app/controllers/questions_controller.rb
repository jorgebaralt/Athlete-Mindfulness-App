class QuestionsController < ApplicationController
	before_action :set_question, only: [:show, :update, :destroy]

  # GET /questions
  def index
    # player_id, answered=no
    if params[:pattern_multi] != nil and params[:age_range] != nil and params[:question_type] != nil and params[:gender] != nil and params[:category] != nil
      @questions = Question.where("pattern_multi = ? AND age_range = ? AND question_type = ? AND gender = ? AND category = ?", params[:pattern_multi], params[:age_range], params[:question_type], params[:gender], params[:category]).order(:question_order)
    elsif params[:pattern_multi] != nil and params[:age_range] != nil and params[:question_type] != nil and params[:gender] != nil
      @questions = Question.where("pattern_multi = ? AND age_range = ? AND question_type = ? AND gender = ?", params[:pattern_multi], params[:age_range], params[:question_type], params[:gender]).order(:question_order)
    elsif params[:category] != nil and params[:pattern_multi] != nil and params[:question_type] != nil and params[:gender] != nil
      @questions = Question.where("category = ? AND pattern_multi = ? AND question_type = ? AND gender = ?", params[:category], params[:pattern_multi], params[:question_type], params[:gender]).order(:question_order)
    elsif params[:category] != nil and params[:age_range] != nil and params[:pattern_multi] != nil and params[:gender] != nil
      @questions = Question.where("category = ? AND age_range = ? AND pattern_multi = ? AND gender = ?", params[:category], params[:age_range], params[:pattern_multi], params[:gender]).order(:question_order)
    elsif params[:category] != nil and params[:age_range] != nil and params[:question_type] != nil and params[:pattern_multi] != nil
      @questions = Question.where("category = ? AND age_range = ? AND question_type = ? AND pattern_multi = ?", params[:category], params[:age_range], params[:question_type], params[:pattern_multi]).order(:question_order)
    elsif params[:category] != nil and params[:age_range] != nil and params[:question_type] != nil and params[:gender] != nil
      @questions = Question.where("category = ? AND age_range = ? AND question_type = ? AND gender = ?", params[:category], params[:age_range], params[:question_type], params[:gender]).order(:question_order)
    elsif params[:category] != nil and params[:age_range] != nil and params[:question_type] != nil
      @questions = Question.where("category = ? AND age_range = ? AND question_type = ?", params[:category], params[:age_range], params[:question_type]).order(:question_order)
    elsif params[:gender] != nil and params[:age_range] != nil and params[:question_type] != nil
      @questions = Question.where("gender = ? AND age_range = ? AND question_type = ?", params[:gender], params[:age_range], params[:question_type]).order(:question_order)
    elsif params[:pattern_multi] != nil and params[:age_range] != nil and params[:question_type] != nil
      @questions = Question.where("pattern_multi = ? AND age_range = ? AND question_type = ?", params[:pattern_multi], params[:age_range], params[:question_type]).order(:question_order)
    elsif params[:gender] != nil and params[:category] != nil and params[:question_type] != nil
      @questions = Question.where("gender = ? AND category = ? AND question_type = ?", params[:gender], params[:category], params[:question_type]).order(:question_order)
    elsif params[:pattern_multi] != nil and params[:category] != nil and params[:question_type] != nil
      @questions = Question.where("pattern_multi = ? AND category = ? AND question_type = ?", params[:pattern_multi], params[:category], params[:question_type]).order(:question_order)
    elsif params[:gender] != nil and params[:pattern_multi] != nil and params[:question_type] != nil
      @questions = Question.where("gender = ? AND pattern_multi = ? AND question_type = ?", params[:gender], params[:pattern_multi], params[:question_type]).order(:question_order)
    elsif params[:gender] != nil and params[:category] != nil and params[:age_range] != nil
      @questions = Question.where("gender = ? AND category = ? AND age_range = ?", params[:gender], params[:category], params[:age_range]).order(:question_order)
    elsif params[:pattern_multi] != nil and params[:category] != nil and params[:age_range] != nil
      @questions = Question.where("pattern_multi = ? AND category = ? AND age_range = ?", params[:pattern_multi], params[:category], params[:age_range]).order(:question_order)
    elsif params[:gender] != nil and params[:pattern_multi] != nil and params[:age_range] != nil
      @questions = Question.where("gender = ? AND pattern_multi = ? AND age_range = ?", params[:gender], params[:pattern_multi], params[:age_range]).order(:question_order)
    elsif params[:gender] != nil and params[:category] != nil and params[:pattern_multi] != nil
      @questions = Question.where("gender = ? AND category = ? AND pattern_multi = ?", params[:gender], params[:category], params[:pattern_multi]).order(:question_order)
    elsif params[:gender] != nil and params[:category] != nil
      @questions = Question.where("gender = ? AND category = ?", params[:gender], params[:category]).order(:question_order)
    elsif params[:gender] != nil and params[:pattern_multi] != nil
      @questions = Question.where("gender = ? AND pattern_multi = ?", params[:gender], params[:pattern_multi]).order(:question_order)
    elsif params[:age_range] != nil and params[:category] != nil
      @questions = Question.where("age_range = ? AND category = ?", params[:age_range], params[:category]).order(:question_order)
    elsif params[:age_range] != nil and params[:gender] != nil
      @questions = Question.where("age_range = ? AND gender = ?", params[:age_range], params[:gender]).order(:question_order)
    elsif params[:age_range] != nil and params[:pattern_multi] != nil
      @questions = Question.where("age_range = ? AND pattern_multi = ?", params[:age_range], params[:pattern_multi]).order(:question_order)
    elsif params[:question_type] != nil and params[:age_range] != nil
      @questions = Question.where("question_type = ? AND age_range = ?", params[:question_type], params[:age_range]).order(:question_order)
    elsif params[:question_type] != nil and params[:gender] != nil
      @questions = Question.where("question_type = ? AND gender = ?", params[:question_type], params[:gender]).order(:question_order)
    elsif params[:question_type] != nil and params[:category] != nil
      @questions = Question.where("question_type = ? AND category = ?", params[:question_type], params[:category]).order(:question_order)
    elsif params[:pattern_multi] != nil and params[:category] != nil
      @questions = Question.where("pattern_multi = ? AND category = ?", params[:pattern_multi], params[:category]).order(:question_order)
    elsif params[:question_type] != nil and params[:pattern_multi] != nil
      @questions = Question.where("question_type = ? AND pattern_multi = ?", params[:question_type], params[:pattern_multi]).order(:question_order)
    elsif params[:pattern_multi] != nil
      @questions = Question.where("pattern_multi = ?", params[:pattern_multi]).order(:question_order)
    elsif params[:gender] != nil
      @questions = Question.where("gender = ?", params[:gender]).order(:question_order)
    elsif params[:age_range] != nil
      @questions = Question.where("age_range = ?", params[:age_range]).order(:question_order)
    elsif params[:question_type] != nil
      @questions = Question.where("question_type = ?", params[:question_type]).order(:question_order)
    elsif params[:category] != nil
      @questions = Question.where("category = ?", params[:category]).order(:question_order)
    else
      @questions = Question.all.order(:question_order)
    end
    json_response(@questions)
  end

  # POST /questions
  def create
    @question = Question.create!(question_params)
    json_response(@question, :created)
  end

  # GET /questions/:id
  def show
    json_response(@question)
  end

  # PUT /questions/:id
  def update
    @question.update(question_params)
    if params[:question_order] != nil
      answer = Answer.find_by(question_id: @question[:id])
      answer.update(question_order: params[:question_order])
    end
    head :no_content
  end

  # DELETE /questions/:id
  def destroy
    @question.destroy
    head :no_content
  end

  private

  def question_params
    # whitelist params
    params.permit( :category, :age_range, :gender, :question_type, :options, :question_text, :coach_id, :player_id , :pattern_multi, :question_order)
  end

  def set_question
    @question = Question.find(params[:id])
  end
end
