class UsersController < ApplicationController
	before_action :set_user, only: [:show, :update, :destroy]
  before_action :authenticate_user!, only: [:destroy]
  # before_action :createChannel, only: [:create]
  include Channels


  # GET /users
  def index
    if params[:is_coach] != nil and params[:age_range] != nil and params[:gender] != nil and params[:is_coach] != nil
      @users = User.where("is_coach = ? AND age_range = ? AND gender = ? AND is_coach = ?", params[:is_coach], params[:age_range], params[:gender], params[:is_coach])
    elsif params[:is_coach] != nil and params[:age_range] != nil and params[:gender] != nil
      @users = User.where("is_coach = ? AND age_range = ? AND gender = ?", params[:is_coach], params[:age_range], params[:gender])
    elsif params[:coach_id] != nil and params[:is_coach] != nil and params[:gender] != nil
      @users = User.where("coach_id = ? AND is_coach = ? AND gender = ?", params[:coach_id], params[:is_coach], params[:gender])
    elsif params[:coach_id] != nil and params[:age_range] != nil and params[:is_coach] != nil
      @users = User.where("coach_id = ? AND age_range = ? AND is_coach = ?", params[:coach_id], params[:age_range], params[:is_coach])
    elsif params[:coach_id] != nil and params[:age_range] != nil and params[:gender] != nil
      @users = User.where("coach_id = ? AND age_range = ? AND gender = ?", params[:coach_id], params[:age_range], params[:gender])
    elsif params[:coach_id] != nil and params[:gender] != nil
      @users = User.where("coach_id = ? AND gender = ?", params[:coach_id], params[:gender])
    elsif params[:gender] != nil and params[:age_range] != nil
      @users = User.where("gender = ? AND age_range = ?", params[:gender], params[:age_range])
    elsif params[:gender] != nil and params[:is_coach] != nil
      @users = User.where("gender = ? AND is_coach = ?", params[:gender], params[:is_coach])
    elsif params[:coach_id] != nil and params[:age_range] != nil
      @users = User.where("coach_id = ? AND age_range = ?", params[:coach_id], params[:age_range])
    elsif params[:coach_id] != nil and params[:is_coach] != nil
      @users = User.where("coach_id = ? AND is_coach = ?", params[:coach_id], params[:is_coach])
    elsif params[:is_coach] != nil and params[:age_range] != nil
      @users = User.where("is_coach = ? AND age_range = ?", params[:is_coach], params[:age_range])
    elsif params[:is_coach] != nil
      @users = User.where("is_coach = ?", params[:is_coach])
    elsif params[:coach_id] != nil
      @users = User.where("coach_id = ?", params[:coach_id])
    elsif params[:age_range] != nil
      @users = User.where("age_range = ?", params[:age_range])
    elsif params[:gender] != nil
      @users = User.where("gender = ?", params[:gender])
    else
      @users = User.all
    end
    json_response(@users)
  end

  # POST /users
  def create
    if User.find(params[:coach_id])[:is_coach] == 1
      @user = User.create!(user_params)
      json_response(@user, :created)
    else
      json_response({ message: "Coach does not exist!" }, :not_found)
    end
  end

  # GET /users/:id
  def show
    json_response(@user)
  end

  # PUT /users/:id
  def update
    @user.update(user_params)
    head :no_content
  end

  # DELETE /users/:id
  def destroy
    @user.destroy
    deleteChannel(@user.id)
    head :no_content
  end

  private

  def user_params
    # whitelist params
    params.permit( :first_name, :last_name, :email, :password, :password_confirmation, :phone, :gender, :points, :coach_id, :age, :age_range, :coach_name, :is_coach)
  end

  def set_user
    @user = User.find(params[:id])
  end

end
