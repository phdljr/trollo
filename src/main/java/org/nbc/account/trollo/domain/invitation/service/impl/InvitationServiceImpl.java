package org.nbc.account.trollo.domain.invitation.service.impl;

import lombok.RequiredArgsConstructor;
import org.nbc.account.trollo.domain.board.entity.Board;
import org.nbc.account.trollo.domain.board.repository.BoardRepository;
import org.nbc.account.trollo.domain.invitation.exception.InvitationDomainException;
import org.nbc.account.trollo.domain.invitation.service.InvitationService;
import org.nbc.account.trollo.domain.user.entity.User;
import org.nbc.account.trollo.domain.user.repository.UserRepository;
import org.nbc.account.trollo.domain.userboard.entity.UserBoard;
import org.nbc.account.trollo.domain.userboard.entity.UserBoardRole;
import org.nbc.account.trollo.domain.userboard.repository.UserBoardRepository;
import org.nbc.account.trollo.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {

  private final UserBoardRepository userBoardRepository;
  private final UserRepository userRepository;
  private final BoardRepository boardRepository;

  @Override
  public void createInvitation(Long boardId, Long userId, User user) {

    //check if the host is inviting oneself
    User guest = getGuestById(userId, user);

    Board board = boardRepository.findById(boardId).orElseThrow(() ->
        new InvitationDomainException(ErrorCode.NOT_FOUND_BOARD));

    //check if userboard doesn't exists with the boardId and userId
    if (userBoardRepository.existsByBoardIdAndUserId(boardId, userId)) {
      throw new InvitationDomainException(ErrorCode.ALREADY_EXIST_INVITATION);
    }

    UserBoard invitation = UserBoard.builder()
        .user(guest)
        .board(board)
        .role(UserBoardRole.WAITING)
        .build();
    userBoardRepository.save(invitation);

  }

  private User getGuestById(Long userId, User user) {

    User guest = userRepository.findById(userId).orElseThrow(() ->
        new InvitationDomainException(ErrorCode.NOT_FOUND_USER));

    if (guest.getId().equals(user.getId())) {
      throw new InvitationDomainException(ErrorCode.HOST_CANNOT_BE_INVITED);
    }

    return guest;

  }

}