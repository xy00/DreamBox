//
//  DBFrameLayout.h
//  DreamBox_iOS
//
//  Created by zhangchu on 2021/1/14.
//

#import <Foundation/Foundation.h>
#import "DBFrameModel.h"

NS_ASSUME_NONNULL_BEGIN

@interface DBFrameLayout : NSObject

+ (UIEdgeInsets)contentRectEdgewithModel:(DBFrameModel *)model;

+ (void)frameLayoutWithView:(UIView *)view withModel:(DBFrameModel *)model contentSize:(CGSize)size edgeInsets:(UIEdgeInsets)edgeInsets;

@end

NS_ASSUME_NONNULL_END
