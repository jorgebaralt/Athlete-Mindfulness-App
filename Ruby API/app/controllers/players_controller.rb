class PlayersController < ApplicationController
	before_action :set_player, only: [:show, :update, :destroy]
  before_action :authenticate_player!, only: [:destroy]
  # before_action :createChannel, only: [:create]
  include Channels


  # GET /players
  def index
    if params[:coach_id] != nil and params[:age_range] != nil and params[:gender] != nil
      @players = User.where("coach_id = ? AND age_range = ? AND gender = ? AND is_coach=0", params[:coach_id], params[:age_range], params[:gender])
    elsif params[:coach_id] != nil and params[:gender] != nil
      @players = User.where("coach_id = ? AND gender = ? AND is_coach=0", params[:coach_id], params[:gender])
    elsif params[:gender] != nil and params[:age_range] != nil
      @players = User.where("gender = ? AND age_range = ? AND is_coach=0", params[:gender], params[:age_range])
    elsif params[:coach_id] != nil and params[:age_range] != nil
      @players = User.where("coach_id = ? AND age_range = ? AND is_coach=0", params[:coach_id], params[:age_range])
    elsif params[:coach_id] != nil
      @players = User.where("coach_id = ? AND is_coach=0", params[:coach_id])
    elsif params[:age_range] != nil
      @players = User.where("age_range = ? AND is_coach=0", params[:age_range])
    elsif params[:gender] != nil
      @players = User.where("gender = ? AND is_coach=0", params[:gender])
    else
      @players = User.where("is_coach = 0")
    end
    json_response(@players)
  end

  # POST /players
  def create
    @player = User.create!(player_params)
    coach = User.find(@player.coach_id)
    @player.coach_name = coach.first_name+" "+coach.last_name
    @player.is_coach = 0
    @player.save
    json_response(@player, :created)
  end

  # GET /players/:id
  def show
    json_response(@player)
  end

  # PUT /players/:id
  def update
    @player.update(player_params)
    head :no_content
  end

  # DELETE /players/:id
  def destroy
    @player.destroy
    deleteChannel(@player.id)
    head :no_content
  end

  private

  def player_params
    # whitelist params
    params.permit( :first_name, :last_name, :email, :password, :password_confirmation, :phone, :gender, :points, :coach_id, :age, :age_range, :coach_name)
  end

  def set_player
    @player = User.find(params[:id])
  end

end
